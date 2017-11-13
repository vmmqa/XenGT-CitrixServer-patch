#include <linux/module.h>
#include <linux/moduleparam.h>
#include <linux/init.h>

#include <linux/kernel.h>	/* printk() to debug */
#include <linux/slab.h>		/* kmalloc() */
#include <linux/fs.h>		/* everything... */
#include <linux/errno.h>	/* error codes */
#include <linux/types.h>	/* size_t */
#include <linux/fcntl.h>	/* O_ACCMODE */
#include <linux/cdev.h>		/* char driver */
#include <asm/system.h>		/* cli(), *_flags */
#include <asm/uaccess.h>	/* copy_*_user */
#include <linux/wait.h>		/* for sleep */
#include <linux/sched.h>
#include <linux/spinlock.h>
#include "spin_lock_sleep.h"		/* local definitions */

/*
 * Our parameters which can be set at load time.
 */
int spin_lock_sleep_major = SPIN_LOCK_SLEEP_MAJOR;
int spin_lock_sleep_minor = 0;
int spin_lock_sleep_nr_devs = SPIN_LOCK_SLEEP_NR_DEVS;	/* number of simple char devices */

module_param(spin_lock_sleep_major, int, S_IRUGO);
module_param(spin_lock_sleep_minor, int, S_IRUGO);
module_param(spin_lock_sleep_nr_devs, int, S_IRUGO);

struct spin_lock_sleep_dev *spin_lock_sleep_devices;	/* allocated in spin_lock_sleep_init */

/*
 * Open and close
 */

int spin_lock_sleep_open(struct inode *inode, struct file *filp)
{
	struct spin_lock_sleep_dev *dev;	/* device information */

	dev = container_of(inode->i_cdev, struct spin_lock_sleep_dev, cdev);
	filp->private_data = dev;	/* for other methods */

	return 0;		/* success */
}

int spin_lock_sleep_release(struct inode *inode, struct file *filp)
{
	return 0;
}

/*
 * Spinlock sleep test: read and write
 */

ssize_t spin_lock_sleep_read(struct file * filp, char __user * buf, size_t count,
			loff_t * f_pos)
{
	unsigned long flags = 0;
	struct spin_lock_sleep_dev *dev = filp->private_data;
	ssize_t retval = 0;

	pr_info("%s:prepare to get spin_lock! PID:%i\n", __func__, current->pid);
	spin_lock_irqsave(&dev->lock, flags);
	pr_info("%s:have got the spin_lock! PID:%i\n", __func__, current->pid);

	pr_info("%s:prepare to sleep! PID:%i\n", __func__, current->pid);
	wait_event(dev->wait, (dev->sleep_condition == 1));
	pr_info("%s:have been wake up! PID:%i\n", __func__, current->pid);

	dev->sleep_condition = 0;
	pr_info("%s:prepare to drop spin_lock! PID:%i\n", __func__, current->pid);
	spin_unlock_irqrestore(&dev->lock, flags);
	pr_info("%s:have dropped spin_lock! PID:%i\n", __func__, current->pid);

out:
	return retval;
}

ssize_t spin_lock_sleep_write(struct file * filp, const char __user * buf,
			 size_t count, loff_t * f_pos)
{
	struct spin_lock_sleep_dev *dev = filp->private_data;
	ssize_t retval = 0;	/* value used in "goto out" statements */
	char input;
	unsigned long flags = 0;

	if (copy_from_user(&input, buf, 1)) {
		retval = -EFAULT;
		goto out;
	}

	if (input == 'l')
	{
		pr_info("%s:prepare to get spin_lock! PID:%i\n", __func__, current->pid);
		barrier();
		spin_lock_irqsave(&dev->lock,flags);
		pr_info("%s:have got the spin_lock! PID:%i\n", __func__, current->pid);
	}

	pr_info("%s:prepare to wake up wait queue! PID:%i\n", __func__, current->pid);
	dev->sleep_condition = 1;
	wake_up(&dev->wait);
	pr_info("%s:have been wake up wait queue! PID:%i\n", __func__, current->pid);

	if (input == 'l')
	{
		pr_info("%s:prepare to drop spin_lock! PID:%i\n", __func__, current->pid);
		spin_unlock_irqrestore(&dev->lock, flags);
		pr_info("%s:have dropped spin_lock! PID:%i\n", __func__, current->pid);
	}

out:
	return retval;
}

struct file_operations spin_lock_sleep_fops = {
	.owner = THIS_MODULE,
	.read = spin_lock_sleep_read,
	.write = spin_lock_sleep_write,
	.open = spin_lock_sleep_open,
	.release = spin_lock_sleep_release,
};

/*
 * Finally, the module stuff
 */

/*
 * The cleanup function is used to handle initialization failures as well.
 * Thefore, it must be careful to work correctly even if some of the items
 * have not been initialized
 */
void spin_lock_sleep_cleanup(void)
{
	int i;
	dev_t devno = MKDEV(spin_lock_sleep_major, spin_lock_sleep_minor);

	/* Get rid of our char dev entries */
	if (spin_lock_sleep_devices) {
		for (i = 0; i < spin_lock_sleep_nr_devs; i++) {
			cdev_del(&spin_lock_sleep_devices[i].cdev);
		}
		kfree(spin_lock_sleep_devices);
	}

	/* cleanup_module is never called if registering failed */
	unregister_chrdev_region(devno, spin_lock_sleep_nr_devs);
	printk(KERN_INFO "spin_lock sleep module unloaded!\n");
}

/*
 * Set up the char_dev structure for this device.
 */
static void spin_lock_sleep_setup_cdev(struct spin_lock_sleep_dev *dev, int index)
{
	int err, devno = MKDEV(spin_lock_sleep_major, spin_lock_sleep_minor + index);

	cdev_init(&dev->cdev, &spin_lock_sleep_fops);
	dev->cdev.owner = THIS_MODULE;
	err = cdev_add(&dev->cdev, devno, 1);
	/* Fail gracefully if need be */
	if (err) {
		printk(KERN_ERR "Error %d adding spin_lock_sleep%d", err, index);
		return;
	}

	dev->lock = SPIN_LOCK_UNLOCKED;
	dev->sleep_condition = 0;
	init_waitqueue_head(&dev->wait);

}

int __init spin_lock_sleep_init(void)
{
	int result, i;
	dev_t dev = 0;

/*
 * Get a range of minor numbers to work with, asking for a dynamic
 * major unless directed otherwise at load time.
 */
	if (spin_lock_sleep_major) {
		dev = MKDEV(spin_lock_sleep_major, spin_lock_sleep_minor);
		result =
		    register_chrdev_region(dev, spin_lock_sleep_nr_devs,
					   "spin_lock_sleep");
	} else {
		result =
		    alloc_chrdev_region(&dev, spin_lock_sleep_minor,
					spin_lock_sleep_nr_devs, "spin_lock_sleep");
		spin_lock_sleep_major = MAJOR(dev);
	}
	if (result < 0) {
		printk(KERN_ERR "spin_lock_sleep: can't get major %d\n",
		       spin_lock_sleep_major);
		return result;
	}

	/* 
	 * allocate the devices -- we can't have them static, as the number
	 * can be specified at load time
	 */
	spin_lock_sleep_devices =
	    kmalloc(spin_lock_sleep_nr_devs * sizeof(struct spin_lock_sleep_dev),
		    GFP_KERNEL);
	if (!spin_lock_sleep_devices) {
		result = -ENOMEM;
		goto fail;	/* Make this more graceful */
	}
	memset(spin_lock_sleep_devices, 0,
	       spin_lock_sleep_nr_devs * sizeof(struct spin_lock_sleep_dev));

	/* Initialize each device. */
	for (i = 0; i < spin_lock_sleep_nr_devs; i++) {
		spin_lock_sleep_setup_cdev(&spin_lock_sleep_devices[i], i);
	}

	printk(KERN_INFO "spin_lock sleep module loaded!\n");

	return 0;		/* succeed */

fail:
	spin_lock_sleep_cleanup();
	return result;
}

module_init(spin_lock_sleep_init);
module_exit(spin_lock_sleep_cleanup);

MODULE_DESCRIPTION("spin_lock sleep test module");
MODULE_VERSION("v1.0");
MODULE_AUTHOR("Tekkaman Ninja <tekkamanninja@gmail.com>");
MODULE_LICENSE("Dual BSD/GPL");
