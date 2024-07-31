from flask import Blueprint, jsonify, request
from flask_login import login_required, current_user
from ..models import tasks, usertask, userdetails

fetchtask = Blueprint('fetchtask', __name__)

@fetchtask.route('/tasks', methods=['POST'])
#@login_required
def fetchtasksall():
    data = request.get_json()
    uploaded_userid = data.get('userid')

    all_usertask = usertask.query.filter(usertask.uploaded_userid != uploaded_userid).all()

    json_list = []
    for _usertask in all_usertask:
        usertaskList = _usertask.to_list()
        task = tasks.query.get(usertaskList[0]).to_dict()
        json_list.append(task)

    return jsonify(json_list), 200

@fetchtask.route('/taskcompleted', methods=['POST'])
#@login_required
def fetchtaskcompleted():
    data = request.get_json()
    uploaded_userid = data.get('userid')

    all_usertask = usertask.query.filter(usertask.completed_userid == uploaded_userid).all()

    json_list = []
    for _usertask in all_usertask:
        usertaskList = _usertask.to_list()
        task = tasks.query.get(usertaskList[0]).to_dict()
        json_list.append(task)

    return jsonify(json_list), 200

@fetchtask.route('/taskreserved', methods=['POST'])
#@login_required
def fetchtaskreserved():
    data = request.get_json()
    uploaded_userid = data.get('userid')

    all_usertask = usertask.query.filter(usertask.reserved_userid == uploaded_userid).all()

    json_list = []
    for _usertask in all_usertask:
        usertaskList = _usertask.to_list()
        task = tasks.query.get(usertaskList[0]).to_dict()
        json_list.append(task)
    
    return jsonify(json_list), 200

@fetchtask.route('/taskposted', methods=['POST'])
#@login_required
def fetchtaskposted():
    data = request.get_json()
    uploaded_userid = data.get('userid')

    all_usertask = usertask.query.filter(usertask.uploaded_userid == uploaded_userid).all()

    json_list = []
    for _usertask in all_usertask:
        usertaskList = _usertask.to_list()
        task = tasks.query.get(usertaskList[0]).to_dict()
        json_list.append(task)

    return jsonify(json_list), 200