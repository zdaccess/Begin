from typing import Dict, Any

def add_ingot(purse):
    if len(purse) == 0:
        new_purse = {"gold_ingots": 0}
    else:
        new_purse = {"gold_ingots": int(purse.get("gold_ingots")) + 1}
    return new_purse

def split_booty(*args:Dict[str, Any]) -> Dict[str, Any]:
    combined_purse = {}
    for purse in args:
        for key, value in purse.items():
            if key == "gold_ingots" and value >= 0:
                combined_purse[key] = value;
    return combined_purse, combined_purse, add_ingot(combined_purse)

if __name__ == "__main__":
    result = split_booty({"bananas":22}, {"gold_ingots":23},
                         {"gold_ingots":10}, {"apples":10})
    print(result)
