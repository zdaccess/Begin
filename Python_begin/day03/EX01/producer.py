import json
import redis
import random
import logging
import time

logging.basicConfig(level=logging.INFO, format='%(message)s')

random_number = [1111111111,
                 2222222222,
                 3333333333,
                 4444444444,
                 5555555555,
                 6666666666,
                 7777777777,
                 8888888888,
                 9999999999]
try:
    connect_redis = redis.Redis(
        host = "localhost",
        port = 6379,
        decode_responses = True
    )
    i = 0
    while True:
        from_number = random.choice(random_number)
        to_number = from_number
        while to_number == from_number:
            to_number = random.choice(random_number)
        generate_message = {
            "metadata": {
                "from": from_number,
                "to": to_number
            },
            "amount": random.randint(-5000, 5000)
        }
        message = json.dumps(generate_message)
        logging.info(message)
        connect_redis.publish('name' , message)
        time.sleep(2)
except:
    logging.error("Error! Connect the redis!")