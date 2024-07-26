from flask import Blueprint, request, jsonify
from werkzeug.security import generate_password_hash, check_password_hash
from flask_login import login_user, logout_user, login_required, current_user
from ..models import User, userdetails
from .. import db

auth = Blueprint('auth', __name__)

@auth.route('/register', methods=['POST'])
def register():
    data = request.get_json()
    username = data.get('username')
    password = data.get('password')
    hashed_password = generate_password_hash(password, method='pbkdf2:sha256')

    new_user = User(username=username, password=hashed_password)
    db.session.add(new_user)
    db.session.commit()

    new_usser_details = userdetails(id=new_user.id, username=new_user.username, karmaPoints = 1000.0)
    db.session.add(new_usser_details)
    db.session.commit()

    return jsonify({"message" : "Registered Successfully"}), 200

@auth.route('/login', methods=['POST'])
def login():
    data = request.get_json()
    username = data.get('username')
    password = data.get('password')
    existing_user = User.query.filter_by(username=username).first()

    if existing_user and check_password_hash(existing_user.password, password):
        login_user(existing_user)
        return jsonify({"userid": current_user.id, "taskid": 0}), 200
    else:
        return jsonify({"message": "Invalid email or password"}), 401
    
@auth.route('/userdetails' ,  methods=['POST'])
def fetchuserdetails():
    data = request.get_json()
    userid = data.get('userid')
    user = userdetails.query.filter(userdetails.id == userid).first()
    user = user.to_dict()
    return jsonify(user), 200
