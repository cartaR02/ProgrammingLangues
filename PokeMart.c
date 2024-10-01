#include "PokeMart.h"

int main(){
	
	FILE *inf = openFile();
	char input[MY_CHAR_MAX];
	struct item *bag[MY_BAG_MAX];
	int bagIndex = 0;

	while(fgets(input, MY_CHAR_MAX, inf) != NULL) {
		
		//Splitting an input string into two substrings, split on the delim char \t, using this rather than strtok();

		//First substring is the items name, the second the items price
		//First let's calculate the length of both substrings
		int nameLen = 0;
		int priceLen = 0;
		int delimIndex = -1;

		//look at every character in the line of input we just read from the file
		for(int i = 0; i < strlen(input); i++){
			//When the current char is the delimiter character \t
				//The length of the name substring is the index of the delimiter (i.e. delim is at index 4, so name substring is from 0-3, which is a length of 4)
				//The length of the price substring can be found by taking the length of the string read from a file, 
					//subtracting the length of the name substring, subtract 1 to account for the delim character, 
					//then subtract 1 again to account for the \n character at the end of the input line
			if(input[i] == '\t'){
				delimIndex = i;
				nameLen = i;
				priceLen = strlen(input) - nameLen - 1 - 1; 
			}
		}
		//Assuming that the input file only has good data. delimIndex will only be -1 here if no delim was found in the input string. We'd put error handling here if we cared
		if(delimIndex == -1){
			printf("WEIRD and BAD!!\n");
			continue;
		}

		//Knowing the sizes of our substrings, we can allocate the exact amount of memory needed to store them
		//NOTEL standard array notation (i.e. char *name[nameLen+1]) doesn't work. Because to use that notation, C requires the size of the array be known at compile time!
		//NOTE: it's substring length + 1 to account for the null character \0. All strings must be null terminated! 
		char *name = malloc((nameLen + 1) * sizeof(char));
		char *strPrice = malloc((priceLen + 1) * sizeof(char));

		//Copy the individual substring characters from the line of input into the substring character array
		for(int i = 0; i < nameLen; i++){
			name[i] = input[i];
		}
		//and don't forget to add the null terminator 
		name[nameLen] = '\0';

		//Do the same for the other substring
		int j = delimIndex + 1;
		for(int i = 0; i < priceLen; i++){
			strPrice[i] = input[j];
			j++;
		}
		strPrice[priceLen] = '\0';


		//Convert string to int
		//strtol is included from string.h and reads numeric characters until an invalid char is encountered 
		char *endptr;
		int price = strtol(strPrice, &endptr, 10);

		//We can do error checking here based on the value of endptr. But we don't because we trust the input data is good.

		//Once we've converted strPrice into an int, we don't need it anymore. But we manually allocated it's memory!
		//We free the memory here to prevent a memory leak
		free(strPrice);

		//All items with a price of 9999 are glitch items which are not obtainable in game and should not be added to the bag
		//So we use continue to skip this item and move onto the next item, i.e. read the next line of the input file
		//But we manually allocated memory for our substring! We need to free that memory here or we'll cause a memory leak
		if(price == 9999){
			free(name);
			continue;
		}

		//Manually allocate memory for an item struct 
		struct item* anItem = malloc(sizeof(struct item));

		//Initalize the struct properties
		anItem->name = name;
		anItem->price = price;

	
		//If the bag array has empty spaces just drop the item in at the end
		if(bagIndex < MY_BAG_MAX){
			bag[bagIndex] = anItem;
			bagIndex++;			
		}
		//Otherwise find the least costly item and replace it with our new item
		else{
			int cheapest = bag[0]->price;
			int cheapestIndex = 0;

			for(int i = 0; i < MY_BAG_MAX; i++){
				if(bag[i]->price < cheapest){
					cheapest = bag[i]->price;
					cheapestIndex = i;
				}
			}
			//If the new item is more expensive than the cheapest item in the bag, replace the cheapest item with the new item
			//Once we've done that, we no longer need the old item anymore. But we manually allocated memory for that item struct, so we must free it!
			if(cheapest < anItem->price){
				struct item* oldItem = bag[cheapestIndex];
				bag[cheapestIndex] = anItem;
				//NOTE: Because both the struct itself and it's name property are allocated manually, we need to free them both
				free(oldItem->name);
				free(oldItem);
			}
			//If the new item is cheaper than the items already in the bag, we don't need the new item at all anymore
			//So we free both the name property and the struct itself, no leaks! 
			else{
				free(anItem->name);
				free(anItem);
			}
		}
	}

	bagSort(bag);
	
	for(int i = 0; i < MY_BAG_MAX; i++){
		printf("%s %d \n",bag[i]->name,bag[i]->price);
	}

	//Lastly, let's free up all the remaining memory on the way out
	//Free all the manually allocated memory for all items in the bag
	for(int i = 0; i < MY_BAG_MAX; i++){
		free(bag[i]->name);
		free(bag[i]);
	}
	//Close our file pointer to free that memory! 
	fclose(inf);




}

//Ugly insertion sort so the final output looks cute
//NOTE: parameter is **aBag. Which is a pointer to a pointer. aBag stores a pointer to an array of pointers.
void bagSort(struct item **aBag){
	int sortedIndex = 0;
	for(int i = 0; i < MY_BAG_MAX; i++){
		int largest = aBag[sortedIndex]->price;
		int largestIndex = sortedIndex;

		for(int j = sortedIndex; j < MY_BAG_MAX; j++){
			if(aBag[j]->price > largest){
				largest = aBag[j]->price;
				largestIndex = j;
			}
		}

		struct item *temp = aBag[sortedIndex];
		aBag[sortedIndex] = aBag[largestIndex];
		aBag[largestIndex] = temp;
		sortedIndex++;

	}
}

FILE* openFile(){
	//initialize file pointer to NULL 
	FILE *infile = NULL; 
	//Define a character array to store the name of the file to read and write
	char filename[MY_CHAR_MAX];  
	//Prompt the user to input a filename and continue to prompt the user until they enter a correct one
	while(infile == NULL) {  
		printf("Enter filename: ");  
		scanf("%s",filename);
		//When given a filename, use fopen to create a new file pointer. 
			//If fopen can not find the file, it returns null
		infile = fopen(filename, "r+");
		if(infile == NULL){ 
			printf("ERROR: file %s cannot be opened\n", filename);
	  	}
	}
	return infile;
}