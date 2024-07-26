from flask import Blueprint, jsonify, request
from flask_login import login_required
from ..models import tasks, usertask, userdetails
from .. import db

taskstatus = Blueprint('taskstatus', __name__)

@taskstatus.route('/reserve', methods=['POST'])
#@login_required
def reserve():
    data = request.get_json()
    taskid = data.get('taskid')
    reserved_userid = data.get('userid')
    #one who reserved it

    existing_task = tasks.query.filter_by(id = taskid).first()
    existing_usertask = usertask.query.filter_by(taskid = taskid).first()
    existing_userdetails = userdetails.query.filter_by(id = reserved_userid).first()

    if not existing_task:
        return jsonify({"message": "Task details not found"}), 404
    else:
        if existing_task.reserved:
            return jsonify({"message": "Task is already reserved"}), 400
        else:
            existing_task.reserved = True
            existing_usertask.reserved_userid = reserved_userid
            temp = existing_userdetails.reserved + 1
            existing_userdetails.reserved = temp
            db.session.commit()
            return jsonify({"message": "Task reserved successfully"}), 200

@taskstatus.route('/complete', methods=['POST'])
#@login_required
def complete():
    data = request.get_json()
    taskid = data.get('taskid')

    existing_task = tasks.query.filter_by(id = taskid).first()
    existing_usertask = usertask.query.filter_by(taskid = taskid).first()
    reserved_userid = existing_usertask.reserved_userid
    uploaded_userid = existing_usertask.uploaded_userid
    existing_userdetails_reserved = userdetails.query.filter_by(id = reserved_userid).first()
    existing_userdetails_uploaded = userdetails.query.filter_by(id = uploaded_userid).first()

    if not existing_task:
        return jsonify({"message": "Task details not found"}), 404
    else:
        if existing_task.completed:
            return jsonify({"message": "Task is already completed"}), 400
        else:
            existing_task.completed = True
            existing_usertask.completed_userid = existing_usertask.reserved_userid
            existing_usertask.reserved_userid = None
            temp = existing_userdetails_reserved.completed + 1
            existing_userdetails_reserved.completed = temp
            temp = existing_userdetails_reserved.reserved - 1
            existing_userdetails_reserved.reserved = temp
            temp = existing_userdetails_reserved.karmaPoints + existing_task.karmaPoints
            existing_userdetails_reserved.karmaPoints = temp
            temp = existing_userdetails_uploaded.karmaPoints - existing_task.karmaPoints
            existing_userdetails_uploaded.karmaPoints = temp

            db.session.commit()
            return jsonify({"message": "Task completed successfully"}), 200