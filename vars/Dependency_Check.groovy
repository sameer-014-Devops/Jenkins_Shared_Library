def call() {
    
    try {
        echo "########## Starting Dependency Check ##########"
        dependencyCheck additionalArguments: "--scan ./", odcInstallation: 'OWASP'
        dependencyCheckPublisher pattern: '**/dependency-check-report.xml'
        echo "########## Dependency Check Completed ##########"
    } catch (Exception e) {
        echo "########## Dependency Check Failed ##########"
        echo "Error: ${e.message}"
        throw e
    }
    
}
