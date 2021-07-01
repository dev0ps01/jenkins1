def call (Map params =  [:] ) {
    def args = [
            NEXUS_IP: '172.31.9.137',
    ]
    args << params

    pipeline {

        agent {

            label "${args.SLAVE_LABEL}"
        }

        triggers {
            pollSCM('*/2 * * * 1-5')
        }
        environment {
            COMPONENT = "${args.COMPONENT}"
            NEXUS_IP = "${args.NEXUS_IP}"
            PROJECT_NAME = "${args.PROJECT_NAME}"
            SLAVE_LABEL = "${args.SLAVE_LABEL}"
            APP_TYPE = "${args.APP_TYPE}"
        }

        stages {
            stage('Build code & install dependices') {
                step {
                    script {
                        build = new nexus()
                        build.code_build("${APP_TYPE}", "${COMPONENT}")
                    }
                }
            }

            stage('prepare artifacts') {
                steps {
                    script {
                        prepare = new nexus()
                        prepare.make_artifacts("${APP_TYPE}", "${COMPONENT}")
                    }
                }
            }
            stage('upload artifact') {
                steps {
                    script {
                        prepare = new nexus()
                        prepare.nexus(component)
                    }

                }
            }
        }
    }
}

