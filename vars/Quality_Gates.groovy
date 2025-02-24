def call(){
  timeout(time: 2, unit: "MINUTES"){
      waitForQualityGate abortPipeline: false
  }
}
