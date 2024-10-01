#ifndef LINKEDLIST_H
#define LINKEDLIST_H

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "Givens.h"

struct Node* createNode(char* data);

struct Node* createList(FILE* inf);

struct Node* removeNode(struct Node** head, int index);

void traverse(struct Node* head);

void freeNode(struct Node* aNode);

void freeList(struct Node** head);
#endif