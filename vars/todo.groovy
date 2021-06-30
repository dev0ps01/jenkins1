def call (Map params =  [:] )
{
    def args = [
            NEXUS_IP         : '172.31.9.137',
    ]
    args << params

    pipeline {

        agent {

            label "${args.SLAVE_LABEL}"
        }
        environment {
            COMPONENT    = "${args.COMPONENT}"
            NEXUS_IP     = "${args.NEXUS_IP}"
            PROJECT_NAME = "${args.PROJECT_NAME}"
            SLAVE_LABEL  = "${args.SLAVE_LABEL}"
            APP_TYPE     =  "${args.APP_TYPE}"
        }

        stages {
            stage ('prepare artifact for nginx') {
               when {
                   environment name: 'APP_TYPE', value: 'NGINX'
               }
                steps {

                    sh '''

                       echo ${COMPONENT}
                       zip -r ./${COMPONENT}.zip * node_modules dist
                    '''
                }
            }
            stage ('make compile') {
                steps {
                    sh '''
                       mvn compile
                    '''
                }
            }
            stage (' make package') {
                steps {
                    sh '''
                       mvn package
                    '''

                }
            }
            stage ('prepare artifact for nodejs') {
                when {
                    environment name: 'APP_TYPE', value: 'NODEJS'
                }
                steps {

                    sh '''

                       cp target/*.jar users.jar && zip -r  ../${COMPONENT}.zip * users.jar
                    '''
                }
            }
            stage ('prepare artifact go lang') {
                when {
                    environment name: 'APP_TYPE', value: 'GOLANG'
                }
                steps {

                    sh '''

                      zip -r  ../${COMPONENT}.zip * login-ci main.go user.go tracing.go
                    '''
                }
            }
            stage ('upload artifact') {
                steps {
                    sh '''
                      curl -f -v -u admin:vamsi --upload-file frontend.zip http://${NEXUS_IP}:8081/repository/frontend1/frontend.zip

                    '''
                }
            }
        }
    }
}