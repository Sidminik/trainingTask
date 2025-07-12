package co.avbinvest.companyservices.exception;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException(Long id) {
        super("Company " + id + " not found");
    }
}
