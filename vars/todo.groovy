def call ()
{
    pipeline {

        agent any

        stages {
            stage ('prepare artifact') {
                steps {

                    sh '''
              zip -r ./frontend.zip * node_modules dist
           '''
                }
            }
            stage ('upload artifact') {
                steps {
                    sh '''
           curl -f -v -u admin:vamsi --upload-file frontend.zip http://172.31.9.137:8081/repository/frontend1/frontend.zip

          '''
                }
            }
        }
    }
}