public class person {
    protected String name;

    public person(String name){
        this.name = name;
    }

    public boolean isAdmin(){
        return false;
    }

    public void showRole(){
        System.out.println("User: " + name);
    }

    public void accessSystem(){
        System.out.println("Can only view movies");
    }
}
