MODULE_NAME = spin_lock_sleep
MODULE_CONFIG = CONFIG_SPIN_LOCK_SLEEP
CROSS_CONFIG = y
# Comment/uncomment the following line to disable/enable debugging
DEBUG = y



ifneq ($(KERNELRELEASE),)
# Add your debugging flag (or not) to CFLAGS
ifeq ($(DEBUG),y)
  DEBFLAGS = -O -g -DDEBUG # "-O" is needed to expand inlines
else
  DEBFLAGS = -O2
endif
ccflags-y += $(DEBFLAGS)


obj-$($(MODULE_CONFIG)) := $(MODULE_NAME).o
#for Multi-files module
#$(MODULE_NAME)-objs := hello_linux_simple_dep.o ex_output.o

else

#for Local compile
KERNELDIR = /lib/modules/$(shell uname -r)/build
ARCH = x86
CROSS_COMPILE = 
INSTALLDIR := /



################################
PWD := $(shell pwd)

.PHONY: modules modules_install clean

modules:
	$(MAKE) ARCH=$(ARCH) CROSS_COMPILE=$(CROSS_COMPILE) $(MODULE_CONFIG)=m -C $(KERNELDIR) M=$(PWD) modules

modules_install: modules
	$(MAKE) ARCH=$(ARCH) CROSS_COMPILE=$(CROSS_COMPILE) $(MODULE_CONFIG)=m -C $(KERNELDIR) INSTALL_MOD_PATH=$(INSTALLDIR) M=$(PWD) modules_install

clean:
	@rm -rf *.o *~ core .depend .*.cmd *.ko *.mod.c .tmp_versions *.symvers *.order .*.o.d modules.builtin
endif
