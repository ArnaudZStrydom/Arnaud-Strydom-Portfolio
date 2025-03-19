import java.util.Random;

public class AutomatedPlayer extends Player {
    private Random random;
    private Hashmap<String, String> possibleAreas;
    private String jumpIntent;
    private boolean treasureClueFound;
    private int extractedObjectNumber;
    private String extractedComparisonOperator;

    public AutomatedPlayer(PlayerMap gameMap) {
        super(gameMap);
        this.random = new Random();
        this.possibleAreas = new Hashmap<>();
        this.jumpIntent = "";
        for (Object o : gameMap.areas.getKeys()) {
            this.possibleAreas.insert((String) o, (String) o);
        }
    }

    @Override
    public void takeTurn() {
        if (gameMap.currentNode != null && gameMap.currentNode.nodeData.length > gameMap.currentIndexInNode
                && gameMap.currentNode.nodeData[gameMap.currentIndexInNode] != null) {

            GameObject currentObject = (GameObject) gameMap.currentNode.nodeData[gameMap.currentIndexInNode];
            String message = currentObject.visit();
            processMessage(message);
        }

        decideNextAction();
    }

    private void processMessage(String message) {
        if (message.startsWith("A clue: Location is: not")) {
            
            String area = message.substring(25, message.length() - 1);
            possibleAreas.delete(area);
        } else if (message.startsWith("A clue: Location is:")) {
            String area = message.substring(21, message.length() - 1);
            possibleAreas.clear();
            possibleAreas.insert(area, area);
            this.jumpIntent = area;
        }else if (message.startsWith("A clue: The treasure is:")){
            String[] parts = message.split(" ");
            extractedComparisonOperator = parts[5];
            String objectNumberStr = parts[6];
            if (objectNumberStr.endsWith(".")) {
                objectNumberStr = objectNumberStr.substring(0, objectNumberStr.length() - 1);
            }
            extractedObjectNumber = Integer.parseInt(objectNumberStr);
            treasureClueFound = true;
        }
    }

    private void decideNextAction() {
        if (possibleAreas.numValues == 1 && !gameMap.currentAreaName.equals(this.jumpIntent)) {
            gameMap.takeAction(PlayerMap.Action.J);
            return; 
        } else if (gameMap.currentAreaName.equals("")) {
            this.jumpIntent = "swamp"; 
            gameMap.takeAction(PlayerMap.Action.J);
            return; 
        }
    
        if (treasureClueFound) {
            String comparisonOperator = extractedComparisonOperator;
            int currentObjectNumber = extractedObjectNumber;
                if (comparisonOperator.equals(">") && canMoveNext()) {

                    gameMap.takeAction(PlayerMap.Action.N); 
                    comparisonOperator = "";
                    treasureClueFound = false ;

                } else if(comparisonOperator.equals("<=") && canMovePrev()) {
                    gameMap.takeAction(PlayerMap.Action.P); 
                    comparisonOperator = "";
                    treasureClueFound = false ;
                    
                }

                PlayerMap.Action[] actions = PlayerMap.Action.values();
                PlayerMap.Action action = null;
                boolean valid = false;
                while (!valid) {
                    valid = true;
                    int actionIndex = random.nextInt(actions.length);
                    action = actions[actionIndex];
                    switch (action) {
                        case N:
                            valid = canMoveNext();
                            break;
                        case P:
                            valid = canMovePrev();
                            break;
                        case A:
                            valid = canAscend();
                            break;
                        case D:
                            valid = canDescend();
                            break;
                        case J:
                            valid = true;
                            break;
                    }
                }
                
                gameMap.takeAction(action);
                 
        } else {
            
            PlayerMap.Action[] actions = PlayerMap.Action.values();
            PlayerMap.Action action = null;
            boolean valid = false;
            while (!valid) {
                valid = true;
                int actionIndex = random.nextInt(actions.length);
                action = actions[actionIndex];
                switch (action) {
                    case N:
                        valid = canMoveNext();
                        break;
                    case P:
                        valid = canMovePrev();
                        break;
                    case A:
                        valid = canAscend();
                        break;
                    case D:
                        valid = canDescend();
                        break;
                    case J:
                        valid = true;
                        break;
                }
            }
            
            gameMap.takeAction(action);
        }
    }

    private boolean canMoveNext() {
        return gameMap.currentNode != null && gameMap.currentIndexInNode + 1 < gameMap.currentNode.nodeData.length;
    }

    private boolean canMovePrev() {
        return gameMap.currentNode != null && gameMap.currentIndexInNode - 1 >= 0;
    }

    private boolean canAscend() {
        return gameMap.currentNode != null && gameMap.currentNode.parent != null;
    }

    private boolean canDescend() {
        return gameMap.currentNode != null && gameMap.currentNode.descend(gameMap.currentIndexInNode) != null;
    }

    @Override
    public void selectArea() {
        gameMap.selectArea(jumpIntent);
    }

    @Override
    public void close() {
        // Cleanup anything you use here
    }
}


