package com.company.modal;


/**
 * <body>You are reading an intro in class <code>Employee</code>.
 *
 * this class was simply designed for testing.
 * The class for testing located in {@link com.company.demo}

 * For further questions about testing, please post to
 * {@link "https://stackoverflow.com"}.
 * </body>
 * @author Tim
 * @see <a href="www.horstmann.com/corejava.html">The Core Java home page</a>
 * @see "Core Java 2 vol. 2"
 */
public class Employee {
    private int id;
    private String empName;
    private double empSalary;
    private boolean fired = false;

    /**
     * The base interest for every employee
     */
    public static final double BASE_INTEREST = 0.15;

    public Employee () {}

    /**
     * a Constructor contains 3 params
     * @param id
     * @param empName
     * @param empSalary
     */
    public Employee (int id, String empName, double empSalary) {
       this.id = id;
       this.empName = empName;
       this.empSalary = empSalary;
    }

    /**
     * get current object`s id number
     * @return object`s id number
     */
    public int getId() {
        return id;
    }

    /**
     * set current employee`s id
     * @param id the employee`s given id
     * @throws NullPointerException because some unavoidable problems, this exception could be threw if you skipped the arguments check
     */
    public void setId(int id) throws NullPointerException {
        this.id = id;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public double getEmpSalary() {
        return empSalary;
    }

    public void setEmpSalary(double empSalary) {
        this.empSalary = empSalary;
    }

    public boolean isFired() {
        return fired;
    }

    public void setFired(boolean fired) {
        this.fired = fired;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", empName='" + empName + '\'' +
                ", empSalary=" + empSalary +
                ", fired=" + fired +
                '}';
    }
}
