public class admin extends person{

    public admin(String name) {
        super(name);
    }

    @Override
    public boolean isAdmin(){
        return true;
    }

    @Override
    public void showRole(){
        System.out.println("Admin: " + name);
    }

    @Override
    public void accessSystem() {
        System.out.println("Can manage movies and screenings (CRUD access");
    }
}
