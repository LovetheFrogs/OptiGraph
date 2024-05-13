import math

def main():
    run()


def run():
    print("Welcome to Optigraph")
    nodeList = []
    graph = []
    option = -1
    while True:
        menu()
        option = int(input("Select an option > "))
        while option < 1 or option > 3:
            option = int(input("Select an option > "))
        
        if option == 1:
            add_node(nodeList, graph)
        elif option == 2:
            delete_node(nodeList, graph)
        elif option == 3:
            show_nodes(nodeList)


def add_node(nodeList, graph):
    print()
    if not nodeList:
        print("Enter the center node coordinates")
        xCoord = int(input("x = "))
        yCoord = int(input("y = "))
        print("Adding node x: " + str(xCoord) + ", y: " + str(yCoord) + " as center of the graph.")
        graph.append([])
        nodeList.append([xCoord, yCoord, []])
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
        nodeList.append([xCoord, yCoord, []])
        graph.append([0])
        graph[0].append(len(nodeList) - 1)
        calculate_distance(nodeList)


def delete_node(nodeList, graph):
    for i, node in enumerate(nodeList):
        if i == 0:
            print(str(i) + ") " + str(node[:2]) + " CENTER")
        else:
            print(str(i) + ") " + str(node[:2]))
    print()
    toDelete = int(input(("Select a node to delete > ")))
    while toDelete == 0 or not nodeList[toDelete]:
        print("Can't delete center node or already deleted node")
        toDelete = int(input(("Select a node to delete > ")))
    print("Deleted node " + str(toDelete) + " x: " + str(nodeList[toDelete][0]) + ", y:" + str(nodeList[toDelete][1]))
    nodeList[toDelete] = []
    graph.pop(toDelete)
    graph[0].pop(toDelete - 1)
    calculate_distance(nodeList)


def show_nodes(nodeList):
    for i, node in enumerate(nodeList):
        if i == 0:
            print(str(i) + ") " + str(node[:2]) + " CENTER")
        else:
            print(str(i) + ") " + str(node[:2]))


def calculate_distance(nodeList):
    for node in nodeList:
        if node:
            node[2] = []
            for x in nodeList:
                if x:
                    node[2].append(math.sqrt(((x[0] - node[0]) ** 2) + ((x[1] - node[1]) ** 2)))


def menu():
    print()
    print("1) Add a node")
    print("2) Delete a node")
    print("3) Show node list")


if __name__ == "__main__":
    main() 
