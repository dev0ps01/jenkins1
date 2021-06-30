def call (Map params =  [:] ) {
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
        }

        stages {
            stage ('prepare artifact') {
                steps {

                    sh '''

                        echo ${COMPONENT}
                       zip -r ./frontend.zip * node_modules dist
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