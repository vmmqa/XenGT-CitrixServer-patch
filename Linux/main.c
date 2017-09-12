#include <stdio.h>   
#include <stdlib.h>
#include "list.h"
struct person   
{   
    int age;   
    int weight;   
    struct list_head list;   
};   
int main(int argc, char* argv[])   
{   
    struct person *tmp;   
    struct list_head *pos, *n;   
    int age_i, weight_j;   
    struct person person_head;   
    INIT_LIST_HEAD(&person_head.list);   
       
    for(age_i = 10, weight_j = 35; age_i < 40; age_i += 5, weight_j += 5)   
    {   
        tmp =(struct person*)malloc(sizeof(struct person));   
        tmp->age = age_i;   
        tmp->weight = weight_j;   
        list_add(&(tmp->list), &(person_head.list));   //insert before the person_head.list
    }
   
    printf("\n");   
    printf("=========== print the list ===============\n");   
    list_for_each(pos, &person_head.list)   
    {   
        tmp = list_entry(pos, struct person, list);   
        printf("age:%d,  weight: %d \n", tmp->age, tmp->weight);   
    }   
    printf("\n");   
    printf("========== print list after delete a node which age is 20 ==========\n");   
    list_for_each_safe(pos, n, &person_head.list)   
    {   
        tmp = list_entry(pos, struct person, list);   
        if(tmp->age == 20)   
        {   
            list_del_init(pos);   
            free(tmp);   
        }   
    }   
    list_for_each(pos, &person_head.list)   
    {   
        tmp = list_entry(pos, struct person, list);   
        printf("age:%d,  weight: %d \n", tmp->age, tmp->weight);   
    }
   
    list_for_each_safe(pos, n, &person_head.list)   
    {   
        tmp = list_entry(pos, struct person, list);   
        list_del_init(pos);   
        free(tmp);   
    }   
       
    return 0;   
}  
