package za.ac.tutormi.tpg30bd.project;

public class Person {

	private int memberID;
	private String firstName;
	private String secondName;
	private String lastName;
	private String gender;
	private String subject;
	private String tell;
	private String email;
	private double rate;
	private String username;
	private String password;
	private Booking booking;
	
	public Person(int memberID, String firstName, String secondName,
			String lastName, String gender, String subject, String tell,
			String email, double rate, String username, String password, Booking booking) {
		super();
		
		this.memberID = memberID;
		this.firstName = firstName;
		this.secondName = secondName;
		this.lastName = lastName;
		this.gender = gender;
		this.subject = subject;
		this.tell = tell;
		this.email = email;
		this.rate = rate;
		this.username = username;
		this.password = password;
		this.booking = booking;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public int getMemberID() {
		return memberID;
	}

	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTell() {
		return tell;
	}

	public void setTell(String tell) {
		this.tell = tell;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
}
