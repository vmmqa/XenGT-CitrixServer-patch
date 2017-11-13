#include <stdio.h>
#include <stdlib.h>

#define NUM_V 22

typedef struct node *position;


struct node {
    int element;
    position next;
};


void insert_edge(position, int, int);
void print_graph(position, int);
void init_indeg(position, int , int *);
void update_indeg(position, int, int *, int);
void get_seq(position, int, int *, int *);
int find_next(int *, int);

void main()
{
    struct node graph[NUM_V];
    int indeg[NUM_V];
    int seq[NUM_V];
    int i;
    
    // initialize the graph
    for(i=0; i<NUM_V;i++) {
        (graph+i)->element = i; 
        (graph+i)->next    = NULL;
	indeg[i]=0;
    }

    
    insert_edge(graph,0,1);
    insert_edge(graph,0,5);
    insert_edge(graph,1,2);
    insert_edge(graph,1,10);
    insert_edge(graph,2,3);
    insert_edge(graph,3,4);
    insert_edge(graph,7,8);
    insert_edge(graph,9,5);
    insert_edge(graph,9,15);
    insert_edge(graph,10,6);
    insert_edge(graph,10,7);
    insert_edge(graph,10,11);
    insert_edge(graph,11,12);
    insert_edge(graph,11,13);
    insert_edge(graph,13,14);
    insert_edge(graph,13,18);
    insert_edge(graph,15,16);
    insert_edge(graph,16,17);
    insert_edge(graph,17,13);
    insert_edge(graph,17,20);
    insert_edge(graph,19,15);
    insert_edge(graph,20,21);

    print_graph(graph,NUM_V);
    
    init_indeg(graph,NUM_V,indeg);
    get_seq(graph, NUM_V, indeg, seq);
    for (i=0; i<NUM_V;i++) {
        printf("%d,", seq[i]);
    }
}

void init_indeg(position graph, int nv, int *indeg)
{
	int i;
	position p;
	for(i=0;i<NUM_V;i++){
		p=(graph+i)->next;
		while(p!=NULL){
			indeg[p->element]++;
			p=p->next;
		}
	}

/*	for (i=0; i<NUM_V;i++) {
		printf("%d,",indeg[i]);
	}
*/		
}

void print_graph(position graph, int nv) {
    int i;
    position p;
    for(i=0; i<NUM_V; i++) {
        p = (graph + i)->next;
        printf("From =: ");
        while(p != NULL) {
            printf("%d->%d; ", i, p->element);
            p = p->next;
        }
        printf("\n");
    }
}


void insert_edge(position graph,int from, int to)
{
    position np;
    position nodeAddr;

    np = graph + from;

    nodeAddr = (position) malloc(sizeof(struct node));
    nodeAddr->element = to;
    nodeAddr->next    = np->next;
    np->next = nodeAddr;
}

int find_next(int indeg[], int nv) {
	int i;
	for(i=0;i<nv;i++){
		if(indeg[i]==0){
			indeg[i]=-1;
			return i;
			}
	}

	return i;
}

// update indeg when ver is removed
void update_indeg(position graph, int nv, int indeg[], int ver) {
	position p = (graph + ver)->next;
	while(p!=NULL){
                        indeg[p->element]--;
                        p=p->next;
	}
	return;
}

// return the sequence
void get_seq(position graph, int nv, int indeg[], int seq[]){
	for(int i=0;i<nv;i++){
		int j=find_next(indeg,nv);
		if(j!=nv){
	//		printf("%d,",j);
			update_indeg(graph,nv,indeg,j);
			seq[i]=j;
		}else
		{
			printf("error, can't find 0 indeg\n");
			return;
		}
	}

	return;

}
