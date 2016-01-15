#!/bin/bash
JAVA_CMD="/usr/lib/jvm/jre1.8.0_66/bin/java -jar "

start() {
        cd /var/www/servers/crate-demo/bin
        ulimit -c unlimited
	nohup $JAVA_CMD crate-demo-server.jar net.devcouch.Server > ../logs/server.log &
}

stop() {
        LPIProcess=$(ps aux | grep 'crate-demo-server.jar' | grep -v grep | awk '{print $2}')
        kill $LPIProcess
        sleep 20
        kill -9 $LPIProcess
        sleep 5
}


case "$1" in
        start)
                        echo "Starting 'crate demo server'..." >&2
                        start
                ;;
        stop)
                        echo "Stopping 'crate demo server'..." >&2
                        stop
                ;;
        restart)
                        echo "Restarting 'crate demo server'..." >&2
                        stop
                        start
                ;;
        *)
                echo "Usage: '$0'start|stop|restart" >&2        
                exit 0
                ;;
esac

