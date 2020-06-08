# Arakne Map

Map data parser and utilities algorithms for maps and cells.

## Installation

For installing using maven, add this dependency into the `pom.xml` :

```xml
<dependency>
    <groupId>fr.arakne</groupId>
    <artifactId>arakne-map</artifactId>
    <version>0.1-alpha</version>
</dependency>
```

## Usage

### Parsing

The first step for use Dofus map is to parse the map data to extract the cells information.

```java
// Create the serializer
MapDataSerializer serializer = new DefaultMapDataSerializer();

// A cell cache can also be enabled to improve performance when loading multiple maps (like on a server)
DefaultMapDataSerializer serializer = new DefaultMapDataSerializer();
serializer.enableCache();

// Parse the cells data
CellData[] data = serializer.deserialize(mapEntity.mapData());

data[15].layer2().interactive(); // Check if the cell 15 has an interactive object

// Serialize map data
String mapData = serializer.serialize(data);
```

### Map implementation

To use map algorithms and utilities, the maps must implement the map interfaces.

Implements the MapCell :

```java
// Simply extends AbstractCellDataAdapter. 
// For custom implementation, implements MapCell instead.
public class MyCell extends AbstractCellDataAdapter<MyMap> {
    public MyCell(MyMap map, CellData data, int id) {
        super(map, data, id);
    }

    // Implements other methods here...
}
```

Implements the DofusMap :

```java
// Must implements DofusMap with the cell type as template parameter
public class MyMap implements DofusMap<MyCell> {
    final private int id;
    final private MyCell[] cell;
    final private Dimensions dimensions;
    // Other map fields...

    public MyMap(int id, CellData[] data, Dimensions dimensions) {
        this.id = id;
        this.dimensions = dimensions;
        this.cells = createCells(data);
    }

    @Override
    public int size() {
        return cells.length;
    }

    @Override
    public Dimensions dimensions() {
        return dimensions;
    }

    @Override
    public MyCell get(int id) {
        return dimensions;
    }

    // Other map methods...
    
    /**
     * Create cells from map data
     */
    private MyCell[] createCells(CellData[] data) {
        // The cells count is same as data length
        MyCell[] cells = new MyCell[data.length];
    
        // Wrap all cell data into a cell
        for (int cellId = 0; cellId < data.length; ++cellId) {
            cells[cellId] = new MyCell(this, data[cellId], id);
        }

        return cells;
    }
}
```

Now, you can use the cell data to create the dofus map :

```java
// Parse the map data
MapDataSerializer serializer = new DefaultMapDataSerizlizer();
CellData[] data = serializer.deserialize(entity.mapData());

// Create the map instance
MyMap map = new MyMap(entity.id(), data, entity.dimensions());

map.get(15).walkable(); // Check if the cell 15 is walkable
```

### Path & pathfinding

Once the map is created you can compute movement path on it :

```java
// Decorate the map with the Decoder
Decoder<MyCell> decoder = new Decoder<>(map);

// decode the movement game action
// Server side : the start cell is not sent by the client, 
// so it must be provided on the decode method 
Path<MyCell> path = decoder.decode(gameAction.argument(), character.cell());

// Client side : because the first cell is on the path, only the path should be provider
Path<MyCell> path = decoder.decode(gameAction.argument());

// Now, you can validate the path or perform operations on it
path.target(); // Get the target cell
path = path.keepWhile(step -> step.cell().walkable()); // Truncate the path at the first unwalkable cell

// You can encode the path for sending to the client or server
path.encodeWithStartCell(); // Encode the path and force to set the start cell. This is required by the client.
path.encode(); // Simple encode the path. This is the method used by the client to send to the server.
```

You can also generate a path using the pathfinder :

```java
// Get the pathfinder for the map
Decoder<MyCell> decoder = new Decoder<>(map);
Pathfinder<MyCell> pathfinder = decoder.pathfinder();

// Find the path
Path<MyCell> path = pathfinder.findPath(character.cell(), targetCell);

// You can configure the pathfinding algorithm :
pathfinding
    .targetDistance(5) // Stop at 5 cells of the target
    .addFirstCell(false) // Do not add the first cell (for client to server)
    .findPath(character.cell(), targetCell)
;
```

### [CoordinateCell](src/main/java/fr/arakne/utils/maps/CoordinateCell.java)

Helper class for get cell coordinates (i.e. x, y) and some utilities methods.

```java
MyMap map = getMap();

// Decorate the cell 15 with CoordinateCell
CoordinateCell<MyCell> cell15 = new CoordinateCell<>(map.get(15));

// Get coordinates
cell15.x();
cell15.y();

CoordinateCell<MyCell> target = new CoordinateCell<>(map.get(42));
cell15.directionTo(target); // Compute the direction between cells 15 and 42
cell15.distance(target); // Compute the distance between the two cells
```

### Constants

- [Direction](src/main/java/fr/arakne/utils/maps/constant/Direction.java) : Enum of all available direction with extract information
- [CellMovement](src/main/java/fr/arakne/utils/maps/constant/CellMovement.java) : Enum of cell movement values
