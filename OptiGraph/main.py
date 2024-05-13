def main():
    run()


def run():
    print("Welcome to Optigraph")
    nodeList = []
    option = -1
    while True:
        menu()
        option = int(input("Select an option > "))
        while option < 1 or option > 3:
            option = int(input("Select an option > "))
        
        if option == 1:
            add_node(nodeList)
        elif option == 2:
            delete_node(nodeList)
        elif option == 3:
            show_nodes()


def add_node(nodeList):
    print()
    if not nodeList:
        print("Enter the center node coordinates")
        xCoord = int(input("x = "))
        yCoord = int(input("y = "))
        print("Adding node x: " + str(xCoord) + ", y: " + str(yCoord) + " as center of the graph.")
        nodeList.append((xCoord, yCoord))
    else:
        print("Enter the node coordinates")
        xCoord = int(input("x = "))
        yCoord = int(input("y = "))
        while (xCoord, yCoord) in nodeList:
            print("Node is already in graph.")
            print("Enter the node coordinates")
            xCoord = int(input("x = "))
            yCoord = int(input("y = "))
        print("Adding node x: " + str(xCoord) + ", y: " + str(yCoord) + " to the graph.")
        nodeList.append((xCoord, yCoord))


def delete_node(nodeList):
    for i, node in enumerate(nodeList):
        print(str(i) + ") " + str(node))
    print()
    toDelete = int(input(("Select a node to delete > ")))
    while toDelete == 0:
        print("Can't delete center node")
        toDelete = int(input(("Select a node to delete > ")))
    print("Deleted node " + str(toDelete) + " x: " + str(nodeList[toDelete][0]) + ", y:" + str(nodeList[toDelete][1]))
    nodeList.pop(toDelete)


def menu():
    print()
    print("1) Add a node")
    print("2) Delete a node")
    print("3) Show node list")


if __name__ == "__main__":
    main() 
