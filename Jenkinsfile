pipeline {
	agent any
	
   	stages {
        stage('Verify') {
			steps {
				script {
					if("opened" == "$action" || "synchronize" == "$action" || "edited" == "$action") {
						bat 'mvn -B deploy'
					}
				}
			}
		}
        stage('Deploy') {
			steps {
				script {
					if("closed" == "$action" && "develop" == "$target") {
						bat 'mvn -B deploy'
					}
				}
			}
		}
    }
    
}