from flask import Blueprint, jsonify, request
from flask_login import login_required
from ..models import tasks, usertask
from .. import db

newtask = Blueprint('newtask', __name__)

@newtask.route('/newtask', methods=['POST'])
#@login_required
def addtask():
    data = request.get_json()
    title = data.get('title')
    description = data.get('description')
    location = data.get('location')
    karmaPoints = data.get('karmaPoints')
    uploaded_userid = data.get('userid')

    new_task = tasks(title = title, description = description, location = location, karmaPoints = karmaPoints)
    db.session.add(new_task)
    db.session.commit()

    new_usertask = usertask(taskid = new_task.id, uploaded_userid = uploaded_userid)
    db.session.add(new_usertask)
    db.session.commit()

    return jsonify({"message" : "Task added successfully"}), 200