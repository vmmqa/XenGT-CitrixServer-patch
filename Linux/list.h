struct list_head {
	struct list_head *next, *prev;
};
static inline void INIT_LIST_HEAD(struct list_head *list)
{
	list->next = list;
	list->prev = list;
}

/*
 *  * Insert a new entry between two known consecutive entries.
 *   *
 *    * This is only for internal list manipulation where we know
 *     * the prev/next entries already!
 *      */
static inline void __list_add(struct list_head *new,
			      struct list_head *prev,
			      struct list_head *next)
{
	next->prev=new;
	new->next=next;
	new->prev=prev;
	prev->next=new;
}
/**
 *  * list_add - add a new entry
 *   * @new: new entry to be added
 *    * @head: list head to add it after
 *     *
 *      * Insert a new entry after the specified head.
 *       * This is good for implementing stacks.
 *        */
static inline void list_add(struct list_head *new, struct list_head *head)
{
	__list_add(new, head, head->next);
}

/*
 *  * Delete a list entry by making the prev/next entries
 *   * point to each other.
 *    *
 *     * This is only for internal list manipulation where we know
 *      * the prev/next entries already!
 *       */
static inline void __list_del(struct list_head * prev, struct list_head * next)
{
	prev->next=next;
	next->prev=prev;
}
static inline int __list_del_entry_valid(struct list_head *entry)
{
	return 1;
}

static inline void list_del(struct list_head *entry)
{
	__list_del(entry->prev, entry->next);
}
/**
 *  * list_del - deletes entry from list.
 *   * @entry: the element to delete from the list.
 *    * Note: list_empty() on entry does not return true after this, the entry is
 *     * in an undefined state.
 *      */
static inline void __list_del_entry(struct list_head *entry)
{
	if (!__list_del_entry_valid(entry))
		return;

	__list_del(entry->prev, entry->next);
}

/**
 *  * list_del_init - deletes entry from list and reinitialize it.
 *   * @entry: the element to delete from the list.
 *    */
static inline void list_del_init(struct list_head *entry)
{
	__list_del_entry(entry);
	INIT_LIST_HEAD(entry);
}

/**
 *  * list_entry - get the struct for this entry
 *   * @ptr:	the &struct list_head pointer.
 *    * @type:	the type of the struct this is embedded in.
 *     * @member:	the name of the list_head within the struct.
 *      */
#define list_entry(ptr, type, member) \
	container_of(ptr, type, member)
/**
 *  * list_for_each	-	iterate over a list
 *   * @pos:	the &struct list_head to use as a loop cursor.
 *    * @head:	the head for your list.
 *     */
#define list_for_each(pos, head) \
	for (pos = (head)->next; pos != (head); pos = pos->next)

#define offsetof(TYPE, MEMBER) ((size_t) &((TYPE *)0)->MEMBER)

/**
 *  * list_for_each_safe - iterate over a list safe against removal of list entry
 *   * @pos:	the &struct list_head to use as a loop cursor.
 *    * @n:		another &struct list_head to use as temporary storage
 *     * @head:	the head for your list.
 *      */
#define list_for_each_safe(pos, n, head) \
	for (pos = (head)->next, n = pos->next; pos != (head); \
		pos = n, n = pos->next)

/**
 *  * container_of - cast a member of a structure out to the containing structure
 *   * @ptr:	the pointer to the member.
 *    * @type:	the type of the container struct this is embedded in.
 *     * @member:	the name of the member within the struct.
 *      *
 *       */
#define container_of(ptr, type, member) ({			\
	const typeof( ((type *)0)->member ) *__mptr = (ptr);	\
	(type *)( (char *)__mptr - offsetof(type,member) );})