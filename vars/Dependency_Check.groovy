def call(){
  dependencyCheck additionalArguments: '--scan ./ --nvdApiKey $NVD_API_KEY', odcInstallation: 'OWASP'
  dependencyCheckPublisher pattern: '**/dependency-check-report.xml'
}
