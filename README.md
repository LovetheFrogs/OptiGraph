# OptiGraph

Optimum graph creation and distribution for underground networks.

## Downloading

You can either download a pre-compiled version or compile one yourself.

### Download a compiled version

You can head to the releases section and download the latest version. Extract the file and run the jar file.

### Compiling Optigraph for yourself

Assuming you have git installed, run

```git clone https://github.com/LovetheFrogs/OptiGraph```

then run `cd OptiGraph`, compile it with `mvn install` and run the .jar file with `java -jar target/OptiGraph-1.0-shaded.jar`.

## Using OptiGraph

To use the app, just add your nodes/stations to the graph and plot them. You can change the algorithm used by clicking Settings > Change Mode and delete stations by giving an id and pressing the delete node button.

OptiGraph also has functionality to save and load graphs using File menu.

![image](https://github.com/LovetheFrogs/OptiGraph/assets/102818341/f8070dcf-9f5b-442d-ac37-10b012a070e7)

## Use cases

Optigraph can be used to plan out metro systems. One interesting use case is the design of minecraft piston-bolt networks for least space traveled between any two stations.

## Colaborating

You can submit a bug report by filling out the bug template in the issues section. You can also collaborate by submiting a pull request. All reasonable pull requests will be reviewed.

