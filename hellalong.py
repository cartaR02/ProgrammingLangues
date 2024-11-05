def generate_strings(n, alphabet):
    # Base case: if n is 0, return an empty string in a list
    if n == 0:
        return [""]

    # Recursive case: generate all strings of length n - 1
    shorter_strings = generate_strings(n - 1, alphabet)
    all_strings = []

    # Append each character from the alphabet to each of the shorter strings
    for char in alphabet:
        for string in shorter_strings:
            all_strings.append(char + string)

    return all_strings

# Main part of the program
def main():
    n = int(input("Enter the length of the strings (and size of the alphabet): "))
    # Create an alphabet of size n using characters '0' to 'n-1'
    alphabet = [str(i) for i in range(n)]
    
    result = generate_strings(n, alphabet)
    print("Generated strings:")
    for s in result:
        print(s)

# Run the program
if __name__ == "__main__":
    main()
