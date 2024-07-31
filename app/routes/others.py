from flask import Blueprint, jsonify, request
from ..models import tasks, usertask, userdetails
from .. import db

others = Blueprint('others', __name__)

@others.route('/edit', methods=['POST'])
def editTask():
    data = request.get_json()
    task = tasks.query.filter_by(id=data['id']).first()

    task.title = data['title']
    task.description = data['description']
    task.karmaPoints = data['karmaPoints']
    task.location = data['location']
    db.session.commit()

    return jsonify({"message": "Edited"}), 200

@others.route('/reputation', methods=['POST'])
def reputation():
    data = request.get_json()
    existing_usertask = usertask.query.filter_by(taskid = data['id']).first()
    who = data.get('who')
    #True for uploaded, False for completed

    if who:
        existing_user = userdetails.query.filter_by(id = existing_usertask.uploaded_userid).first()
        existing_user.reputation += data['reputation']
        db.session.commit()
    else:
        existing_user = userdetails.query.filter_by(id = existing_usertask.completed_userid).first()
        existing_user.reputation += data['reputation']
        db.session.commit()
    
    return jsonify({"message" : "Rated"}), 200

@others.route('/usersList', methods=['POST'])
def usersList():
    data = request.get_json()
    users = userdetails.query.filter(userdetails.id != data['userid'] ).all()
    list = []
    for user in users:
        list.append(user.name())
    
    return jsonify(list), 200

@others.route('/transactions', methods=['POST'])
def transactions():
    data = request.get_json()
    user1 = data.get('user1')
    user2 = data.get('user2')

    lent = 0.0
    recieved = 0.0
    reserved_byuser1 = []
    reserved_byuser2 = []

    completed_usertasks_lent = usertask.query.filter((usertask.uploaded_userid == user1) & (usertask.completed_userid == user2)).all()
    completed_usertasks_recieved = usertask.query.filter((usertask.uploaded_userid == user2) & (usertask.completed_userid == user1)).all()

    for _task in completed_usertasks_lent:
        list = _task.to_list()
        exisiting_task = tasks.query.get(list[0]).to_dict()
        amount = exisiting_task.get('karmaPoints')
        lent += amount

    for _task in completed_usertasks_recieved:
        list = _task.to_list()
        exisiting_task = tasks.query.get(list[0]).to_dict()
        amount = exisiting_task.get('karmaPoints')
        recieved += amount

    reserved_usertasks_2 = usertask.query.filter((usertask.uploaded_userid == user1) & (usertask.reserved_userid == user2)).all()
    reserved_usertasks_1  = usertask.query.filter((usertask.uploaded_userid == user2) & (usertask.reserved_userid == user1)).all()

    for _task in reserved_usertasks_2:
        list = _task.to_list()
        exisiting_task = tasks.query.get(list[0]).to_dict()
        name = exisiting_task.get('title')
        reserved_byuser2.append(name)

    for _task in reserved_usertasks_1:
        list = _task.to_list()
        exisiting_task = tasks.query.get(list[0]).to_dict()
        name = exisiting_task.get('title')
        reserved_byuser1.append(name)
    
    dictionary = { 'lent': lent, 'received': recieved, 'reservedYou': reserved_byuser1, 'reservedOther': reserved_byuser2}

    return jsonify(dictionary), 200

@others.route('/extra', methods=['POST'])
def extra():
    data = request.get_json()
    user = userdetails.query.filter_by(id = data['id']).first()
    user.karmaPoints += data['points']
    db.session.commit()

    return jsonify({"message" : "Success"}), 200

@others.route('/notify', methods=['POST'])
def notification():
    data = request.get_json()
    for dict in data:
        if(dict['id']>0):
            task = tasks.query.filter_by(id = dict['id']).first()
            task.shown = True
            db.session.commit()
    
    return jsonify({"message" : "Done"}), 200