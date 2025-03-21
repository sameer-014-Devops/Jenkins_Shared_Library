def call() {
    
    try {
        echo "**********Starting Dependency Check**********"
        dependency-check additionalArguments: "--scan ./", odcInstallation: 'OWASP'
        dependency-checkPublisher pattern: '**/dependency-check-report.xml'
        echo "**********Dependency Check Completed**********"
    } catch (Exception e) {
        echo "**********Dependency Check Failed**********"
        echo "Error: ${e.message}"
        throw e
    }
    
}
