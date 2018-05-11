package za.ac.tutormi.tpg30bd.project;

public class Booking extends Person {
	

	private int bookingID;
	private Person person;
	private String fullNames;
	private String emailAddress;
	private int numberOfHours;
	private String subject;
	private double totalAmount;
	private String bookingDate;
	private String bookingTime;

	public Booking(int memberID, String firstName, String secondName,
			String lastName, String gender, String subject, String tell,
			String email, double rate, String username, String password,
			Booking booking) {
		super(memberID, firstName, secondName, lastName, gender, subject, tell, email,
				rate, username, password, booking);
		// TODO Auto-generated constructor stub
	}

	public int getBookingID() {
		return bookingID;
	}

	public void setBookingID(int bookingID) {
		this.bookingID = bookingID;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getFullNames() {
		return fullNames;
	}

	public void setFullNames(String fullNames) {
		this.fullNames = fullNames;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public int getNumberOfHours() {
		return numberOfHours;
	}

	public void setNumberOfHours(int numberOfHours) {
		this.numberOfHours = numberOfHours;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}

	public String getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(String bookingTime) {
		this.bookingTime = bookingTime;
	}
	
	
	
}
