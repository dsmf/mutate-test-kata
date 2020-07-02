package mtk.domain;

import static org.mockito.Mockito.verify;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class CompanyTest {
    private Company company;

    @Before
    public void setUp() {
        this.company = new Company("Megadyne, Inc.");
    }

    @After
    public void tearDown() {
        this.company = null;
    }

    @Test
    public void companyRenamed() {
        String proposedName = "Cybertron Unlimited, Ltd.";

        Company aCompany = Mockito.spy(this.company);

        aCompany.setName(proposedName);

        verify(aCompany).setName(proposedName);

        aCompany.getName();
    }

    @Test
    public void leadingTrailingSpacesRemovedFromEmployeeName() {
        Employee employee1 = new Employee("001", " Bob", 100_000.00);
        Assert.assertEquals("Bob", employee1.getName());
        Employee employee2 = new Employee("002", "Alice  ", 100_000.00);
        Assert.assertEquals("Alice", employee2.getName());
    }

    @Test
    public void employeeAdded() {
        this.company.addEmployee(new Employee("123", "Dave", 100_000.00));
        Assert.assertTrue(this.company.numberOfEmployees() > 0);

        this.company.addEmployee(new Employee("456", "Bob", 50_000.00));
        Assert.assertTrue(this.company.numberOfEmployees() > 0);
    }

    @Test
    public void everybodyGetsRaise() {
        double increaseBy = 0.1; // everybody's salary should go up by this fraction

        double davesOriginalSalary = 100_000.00;

        this.company.addEmployee(new Employee("123", "Dave", davesOriginalSalary));
        this.company.addEmployee(new Employee("456", "Alice", 120_000.00));
        this.company.addEmployee(new Employee("789", "Bob", 110_000.00));

        this.company.everybodyGetsRaiseBy(increaseBy);

        Employee dave = this.company.findEmployeeById("123");

        Assert.assertEquals(davesOriginalSalary * increaseBy, dave.getSalary(), 0.0001);
    }

    @Test
    public void findEmployeeById() {
        this.company.addEmployee(new Employee("123", "Dave", 100_000.00));
        this.company.addEmployee(new Employee("456", "Alice", 100_000.00));
        this.company.addEmployee(new Employee("789", "Bob", 100_000.00));

        Employee hopefullyDave = this.company.findEmployeeById("123");
        Employee hopefullyNoOne = this.company.findEmployeeById("999");
    }

    @Test
    public void employeeNameChanged() {
        this.company.addEmployee(new Employee("123", "Dave", 100_000.00));
        this.company.addEmployee(new Employee("456", "Alice", 100_000.00));
        this.company.addEmployee(new Employee("789", "Bob", 100_000.00));

        Employee employee = this.company.findEmployeeById("123");
        employee.setName("Tommy Lee");
        employee = this.company.findEmployeeById("123");
        System.out.println(employee.getName().equals("Tommy Lee") ? "PASSED" : "FAILED");
    }

    @Test
    public void everybodyGetsRaise_softly() {
        final SoftAssertions softly = new SoftAssertions();

        double increaseBy = 0.1; // everybody's salary should go up by this fraction

        double davesOriginalSalary = 100_000.00;

        this.company.addEmployee(new Employee("123", "Dave", davesOriginalSalary));
        this.company.addEmployee(new Employee("456", "Alice", 120_000.00));
        this.company.addEmployee(new Employee("789", "Bob", 110_000.00));

        this.company.everybodyGetsRaiseBy(increaseBy);

        Employee dave = this.company.findEmployeeById("123");
        Employee alice = this.company.findEmployeeById("456");
        Employee bob = this.company.findEmployeeById("789");

        softly.assertThat(dave.getSalary()).isEqualTo(110_000.00, Assertions.offset(0.001d));
        softly.assertThat(alice.getSalary()).isEqualTo(132_000.00, Assertions.offset(0.001d));
        softly.assertThat(bob.getSalary()).isEqualTo(121_000.00, Assertions.offset(0.001d));

    }

}
