import shapely
import sys
import tkinter as tk
from tkinter import Canvas
from rectpack import newPacker

class CustomCanvas:
    def __init__ (self, height, width):

        self.root = tk.Tk()
        self.canvas = Canvas(self.root, height=height, width=width)
        self.canvas.pack()
        
class Rectangle:
    def __init__ (self, height, width, x=0, y=0):

        # converts to int so the packing library wont get mad
        # was using directly with strings before
        self.height = int(height)
        self.width = int(width)
        self.x = x
        self.y = y

    def __str__(self):
        return "{} {}\n".format(self.height, self.width, self.x, self.y)

def pack(allRect, canvasSize):

    # easier variables names to
    canvasX = canvasSize[0]
    canvasY = canvasSize[1]

    # sets up packer object and the bin with dimentions
    # allowing to add rectangles in bin.
    packer = newPacker()
     
    packer.add_bin(canvasX, canvasY)
    for rectangle in allRect:
        packer.add_rect(rectangle.height, rectangle.width)
    packer.pack()
    placed_rectangles = []
    # retrieving rectangles
    for bin in packer:
        for i, rectangle in enumerate(bin):
            # reassigning the rectangles with the new cordinates
            allRect[i].x = rectangle.x
            allRect[i].y = rectangle.y
            width = rectangle.width
            height = rectangle.height
        
            placed_rectangles.append(Rectangle(rectangle.x, rectangle.y, rectangle.x + width, rectangle.y + height))
    return placed_rectangles

def main():

    percent = "25" #sys.argv[1]
    file = "PercentFill.txt"#sys.argv[2]

    # formats to some number xxPercentFill.txt
    fileName = "{}{}".format(percent,file)
    rectangleList = []
    canvas_array = []
    # opens file and goes line by line
    with open (fileName, 'r') as file:
        # reads first line for canvas and splits into array 
        canvas_size = file.readline().strip()
        canvas_array = canvas_size.split(",")

        for line in file:
            # gets each line of and splits by height and width
            cordinatesArray = line.split(",")
            rectangleList.append(Rectangle(cordinatesArray[0], cordinatesArray[1]))

    # after rectangle list is made finnally calls pack to start packing :)
    correctlyPlacedRectangles = pack(rectangleList, (int(canvas_array[0]), int(canvas_array[1])))

    newCavas = CustomCanvas(int(canvas_array[0]), int(canvas_array[1]))
    for r in correctlyPlacedRectangles:
        newCavas.canvas.create_rectangle(r.height, r.width, r.x, 
                                         r.y, fill='lightblue', outline='black')
    newCavas.canvas.pack()
    newCavas.canvas.mainloop()

# main method duh
if __name__ == "__main__":
    main()