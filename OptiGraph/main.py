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
            delete_node()
        elif option == 3:
            show_nodes()


def add_node(nodeList):
    print()
    if not nodeList:
        print("Enter the center node coordinates")
        xCoord = input("x = ")
        yCoord = input("y = ")
        print("Adding node x: " + xCoord + ", y: " + yCoord + " as center of the graph.")
        nodeList.append((xCoord, yCoord))
    else:
        print("Enter the node coordinates")
        xCoord = input("x = ")
        yCoord = input("y = ")
        while (xCoord, yCoord) in nodeList:
            print("Node is already in graph.")
            print("Enter the node coordinates")
            xCoord = input("x = ")
            yCoord = input("y = ")
        print("Adding node x: " + xCoord + ", y: " + yCoord + " to the graph.")
        nodeList.append((xCoord, yCoord))


def menu():
    print()
    print("1) Add a node")
    print("2) Delete a node")
    print("3) Show node list")


if __name__ == "__main__":
    main() 
