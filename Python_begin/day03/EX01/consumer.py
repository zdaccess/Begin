import redis
import sys
import logging
import json

def check_str(string)-> bool:
    for symbol in string:
        if not symbol.isdigit() and symbol != ',':
            return False
    return True

logging.basicConfig(level=logging.INFO, format='%(message)s')
if len(sys.argv) == 3:
    if sys.argv[1] == "-e":
        if check_str(sys.argv[2]):
            try:
                numbers = sys.argv[2].split(",")
                client = redis.Redis(
                    host='localhost', port=6379, decode_responses = True
                )
                pubsub = client.pubsub()
                chanel = "name"
                pubsub.subscribe(chanel)
                for catch in pubsub.listen():
                    if catch['type'] == 'message':
                        try:
                            message = json.loads(catch['data'])
                            sender = message['metadata']['from']
                            receiver = message['metadata']['to']
                            amount = message['amount']
                            if ((receiver == int(numbers[0]) and amount >= 0)
                            or (receiver == int(numbers[1]) and amount >= 0)):
                                message['metadata']['from'] = receiver
                                message['metadata']['to'] = sender
                                logging.info(f"- {message}")
                            else:
                                logging.info(message)
                        except:
                            pass
            except:
                pass
        else:
            logging.error("Error! It is necessary to enter one or two values "
                          "separated by a comma!")
    else:
        logging.error("Error! It is necessary to set the letter -e!")
else:
    logging.error("Error! Enter 2 arguments!")

