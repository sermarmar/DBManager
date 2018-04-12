import com.cice.db.Manager;

public class Main {

    public static void main(String[] args){
        Manager manager = new Manager();


        manager.ejecutarSelect("SELECT * FROM prueba", Object.class);
    }

}
