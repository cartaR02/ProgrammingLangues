#include "LinkedList.h"


struct Node* createNode(char* data){

    struct Node* newNode = (struct Node*)malloc(sizeof(struct Node));

    // Allocating memory for the data to go into
    // then copying it into the data location
    newNode->data = (char*)malloc(strlen(data) + 1);
    // no memory faliure catching 
    strcpy(newNode->data, data);

    newNode->next = NULL;

    return newNode;
}

struct Node* createList(FILE* inf){
    
    char line[MAX_LINE_SIZE];
    struct Node* head = NULL;
    struct Node* tail = NULL;
    while(fgets(line, MAX_LINE_SIZE, inf) != NULL){

        // Removes null character
        // shamelessly stolen from the assignment
        line[strcspn(line, "\n")] = 0;

        // Instantiate the head when it does not exist
        if (head == NULL){
            struct Node* head = createNode(line);
            // sets tail to head as a itterator
            tail = head;
        }else{
            // sets next to the new node and jumps into
            // the new node to be new tail
            struct Node* tempNode = createNode(line);
            tail->next = tempNode;
            tail = tempNode;
        }
    }
    return head;
}

struct Node* removeNode(struct Node** head, int index){

    if (head->next == NULL || head == NULL || index < 0){
        return NULL;
    }

    for (int i = 0; i < index; i++){
        // check and return if next is null
        // no need to check if current head is null
        // counts as out of bounds
        if (head->next == NULL){
            return NULL;
        }
        head = head->next;
    }
    
    return head;
}

void traverse(struct Node* head){

    while (head != NULL){
        printf("%s\n", head->data);
        head = head->next;
    }
}

void freeNode(struct Node* aNode){
    // does not check if node is null
    free(aNode->data);
    free(aNode);
}

void freeList(struct Node** head){

    while (head != NULL){
        struct Node* tempNode = head->next;
        freeNode(head);
        head = tempNode;
    }
    
}