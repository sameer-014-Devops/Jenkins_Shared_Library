def call(){

    script {

        def ipv4Address = env.tierOneIP
        def filePath = '.env.docker'
        def file = new File(filePath)
    
        if (!file.exists()) {
            error "ERROR: File not found: $filePath"
        }
        
        def currentUrl = file.text
        def newUrl = "VITE_API_PATH=\"http://$ipv4Address:31100\""
        
        if (!currentUrl.contains(newUrl)) {
            file.text = file.text.replaceAll(/VITE_API_PATH.*/, newUrl)
            echo "Updated $filePath with new VITE_API_PATH."
        } else {
            echo "No changes needed in $filePath."
        }

    }
}
