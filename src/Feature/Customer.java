package Feature;

public class Customer extends User
{
	public Customer(String username, String password, String role)
	{
		super(username, password, role);
	}
	
	@Override
	public String toString()
	{
		return "Customer: " + getUsername() + "(" + getRole() + ")";
	}

}
