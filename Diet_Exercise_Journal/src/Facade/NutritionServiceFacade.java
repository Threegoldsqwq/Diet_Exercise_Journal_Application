package Facade;

import DatabaseOperation.RuntimeDatabase;

/**
 * This class is the facade for user to use
 */
public class NutritionServiceFacade {

    public NutritionServiceFacade(){}
    public void displayDiet(int userID){
        RuntimeDatabase runtimeDatabase = RuntimeDatabase.getInstance();
        runtimeDatabase.setId(userID);
        try {
            //runtimeDatabase.displayDietData(runtimeDatabase.getId());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
