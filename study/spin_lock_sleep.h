/*
 * spin_lock_sleep.h -- definitions for the spin_lock sleep test module
 *
 * Copyright (C) 2012 Tekkamanninja
 *
 * The source code in this file can be freely used, adapted,
 * and redistributed in source or binary form, so long as an
 * acknowledgment appears in derived source files.  The citation
 * should list that the code comes from the book "
 * " by Tekkamanninja, published
 * by  .   No warranty is attached;
 * we cannot take responsibility for errors or fitness for use.
 *
 * $Id: spin_lock_sleep.h,v 1.00 28/10/2011 15:14:24  $
 */
#include <linux/spinlock.h>
#include <linux/wait.h>		/* for sleep */

#ifndef _SPIN_LOCK_SLEEP_H_
#define _SPIN_LOCK_SLEEP_H_

#ifndef SPIN_LOCK_SLEEP_MAJOR
#define SPIN_LOCK_SLEEP_MAJOR 0	/* dynamic major by default */
#endif

#ifndef SPIN_LOCK_SLEEP_NR_DEVS
#define SPIN_LOCK_SLEEP_NR_DEVS 4	/* spin_lock_sleep0 through spin_lock_sleep3 */
#endif

struct spin_lock_sleep_dev {
	struct cdev cdev;		/* Char device structure              */
	spinlock_t lock;		/* spin_lock */
	int sleep_condition;	/* for sleep */
    wait_queue_head_t wait;       /* read and write queues */
};

/*
 * The different configurable parameters
 */
extern int spin_lock_sleep_major;	/* main.c */
extern int spin_lock_sleep_nr_devs;

/*
 * Prototypes for shared functions
 */

ssize_t spin_lock_sleep_read(struct file *filp, char __user * buf, size_t count,
			loff_t * f_pos);
ssize_t spin_lock_sleep_write(struct file *filp, const char __user * buf,
			 size_t count, loff_t * f_pos);

#endif /* _SPIN_LOCK_SLEEP_H_ */
