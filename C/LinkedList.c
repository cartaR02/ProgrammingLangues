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

void insertAtEnd(struct Node** head, struct Node* newNode){
    // checking for empy node
    if (newNode == NULL){
        return;
    }
    if (*head == NULL){
        *head = newNode;
        return;
    }
    // iterates through list with temp node
    struct Node* curr = *head;
    while (curr->next != NULL){
        // sets head to next to traverse
        curr = curr->next;
    }
    // once next is null we hit the end
    curr->next = newNode;
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
            head = createNode(line);
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

    if (*head == NULL || index < 0){
        return NULL;
    }

    struct Node* temp = *head;
    // removing first node case
    if (index == 0){
        *head = (*head)->next;
        return temp;
    }

    struct Node* previous = *head;
    // finding the one beofre the end to remove :)
    for (int i = 1; i < index; i++){
        
        // check for the end of the list
        if (previous->next == NULL){
            return NULL;
        }
        previous = previous->next;
    }

    // checking if the node to remove exists
    if (previous->next == NULL){
        return NULL;
    }

    // setting the previous node to point to the temps next 
    // thus removing the middle
    temp = previous->next;
    previous->next = temp->next;
    
    return temp;
}

void traverse(struct Node* head){
    // going through not caring if the next is null
    // only if the current one is null
    while (head != NULL){
        printf("%s\n", head->data);
        head = head->next;
    }
}

void freeNode(struct Node* aNode){
    // does not check if node is null
    if (aNode == NULL){
        return;
    }
    free(aNode->data);
    free(aNode);
}

void freeList(struct Node** head){

    // holds next while it frees the head
    // then sets the next to the current and repeats
    while (*head != NULL){
        struct Node* tempNode = (*head)->next;
        freeNode(*head);
        *head = tempNode;
    }
    
}