pipeline {
    agent {
        label "ANSIBLE"
    }

    environment {
        UBUNTU_SSH_PASSWORD = credentials('UBUNTU_SSH_PASSWORD')
    }

    parameters {
        choice(name: 'ENV', choices: ['dev', 'prod' ], description: 'Select Environment')
        string(name: 'COMPONENT', defaultValue: '', description: 'Which Component to deploy')
        string(name: 'VERSION', defaultValue: '', description: 'Which Version of Component to deploy')
    }

    stages {

        stage('Find the Server') {
            steps {
                addShortText background: 'blue', color: 'black', borderColor: 'white', text: "ENV = ${ENV}"
                addShortText background: 'cyan', color: 'black', borderColor: 'white', text: "COMPONENT = ${COMPONENT}"
                addShortText background: 'yellow', color: 'black', borderColor: 'white', text: "VERSION = ${VERSION}"
                sh '''
                    aws ec2 describe-instances --filters "Name=tag:Name,Values=${COMPONENT}-${ENV}" --region us-east-1 | jq .Reservations[].Instances[].PrivateIpAddress |xargs -n1 > inv
                '''
            }
        }

        stage('Deploy to DEV Env') {
            when {
                environment name: 'ENV', value: 'dev'
            }
            steps {
                git branch: 'main', url: 'https://github.com/dev0ps01/ansible.git'
                sh '''

                    ansible-playbook -i inv todoapp.yml -t ${COMPONENT} -e COMPONENT=${COMPONENT} -e ENV=${ENV} -e APP_VERSION=${VERSION} -e ansible_password=${UBUNTU_SSH_PASSWORD}
                '''
            }
        }

        stage('Deploy to PROD Env') {
            when {
                environment name: 'ENV', value: 'prod'
            }
            steps {
                sh 'echo ansible-playbook .....'
            }
        }

    }
}