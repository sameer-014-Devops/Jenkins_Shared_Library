def call(){
  
  timeout(time: 3, unit: "MINUTES"){
    
      waitForQualityGate abortPipeline: false
    
  }
  
}
