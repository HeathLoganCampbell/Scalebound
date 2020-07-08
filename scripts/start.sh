
SERVER_ADDRESS=$1
PORT=$2
RAM_MB=$3
SERVER_NAME=$4
SERVER_TYPE=$5

# Download latest jar

# Start server
ssh sprock@$SERVER_ADDRESS 'bash -s' < ./hubInject.sh $PORT $RAM_MB'M' '100' $SERVER_NAME