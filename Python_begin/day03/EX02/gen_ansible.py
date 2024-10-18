import yaml
import os

sep = os.sep
check_ex00 = os.path.join(".." + sep+ "EX00")
check_ex01 = os.path.join(".." + sep+ "EX01")
with open("../../materials/todo.yml", "r") as fp:
    todo = yaml.safe_load(fp)
res = {
    "name": "Ansible Playbook",
    'hosts': "localhost",
    'become': "yes", "tasks": []
}
for package in todo['server']['install_packages']:
    res["tasks"].append({"ansible.builtin.apt": {'name': package}})
for file in todo['server']['exploit_files']:
    res["tasks"].append(
        {"ansible.builtin.copy":
             {
                 "src": f"{check_ex00 if file == 'exploit.py' 
                                        else check_ex01}{sep}{file}",
                "dest": f"{os.path.join(".")}{sep}{file}", "follow": "yes"}}
    )
    if file == "consumer.py":
        args = "-e " + ",".join(todo['bad_guys'])
        cmd = f"python3 {file} {args}"
    else:
        cmd = f"python3 {file}"

    res["tasks"].append({"ansible.builtin.shell": {"cmd": f"{cmd}"}})

    with open("deploy.yml", 'w') as f:
        yaml.dump([res], f, default_flow_style=False)