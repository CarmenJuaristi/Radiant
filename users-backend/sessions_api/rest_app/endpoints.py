from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt
from .models import CustomUser, UserSession, UserVideo
import bcrypt, json, secrets

SESSION_TOKEN_HEADER='Session-Token'

@csrf_exempt
def register(request):
	if request.method != 'POST':
		return JsonResponse({'error':'Unsupported HTTP method'}, status=405)
	body = json.loads(request.body)
	new_username = body.get('username', None)
	if new_username == None:
		return JsonResponse({'error': 'Missing username in request body'}, status=400)
	new_email = body.get('email', None)
	if new_email == None:
		return JsonResponse({'error': 'Missing email in request body'}, status=400)
	try:
		CustomUser.objects.get(username=new_username)
		CustomUser.objects.get(email=new_email)
	except CustomUser.DoesNotExist:
		# Proceed
		new_password = body.get('password', None)
		if new_password == None:
			return JsonResponse({'error': 'Missing password in request body'}, status=400)

		encrypted_pass = bcrypt.hashpw(new_password.encode('utf8'), bcrypt.gensalt()).decode('utf8')
		new_user = CustomUser()
		new_user.username = new_username
		new_user.email = new_email
		new_user.encrypted_password = encrypted_pass
		new_user.save()
		return JsonResponse({'created':'True'}, status=201)

	# User DOES exist.
	return JsonResponse({'error': 'User with given username or email already exists'}, status=409)
	
@csrf_exempt
def login(request):
	if request.method != 'POST':
		return JsonResponse({'error':'Unsupported HTTP method'}, status=405)
	body = json.loads(request.body)
	username = body.get('username', None)
	if username == None:
		return JsonResponse({'error': 'Missing username in request body'}, status=400)
	try:
		user = CustomUser.objects.get(username=username)
	except CustomUser.DoesNotExist:
		return JsonResponse({'error': 'Username does not exist'}, status=404)
	password = body.get('password', None)
	if password == None:
		return JsonResponse({'error': 'Missing password in request body'}, status=400)
	if bcrypt.checkpw(password.encode('utf8'), user.encrypted_password.encode('utf8')):
		new_session = UserSession()
		new_session.user = user
		new_session.token = secrets.token_hex(10)
		new_session.save()
		return JsonResponse({'created':'True', 'sessionId': new_session.id, 'sessionToken': new_session.token }, status=201)
	else:
		return JsonResponse({'error': 'Password is invalid'}, status=401)


def agent_videos(request, agent_name):
	if request.method == 'GET':
		response = []
		for x in UserVideo.objects.filter(agent_name=agent_name):
			response.append(x.to_obj())
		return JsonResponse(response, safe=False, status=200)
	else:
		return JsonResponse({'error':'Unsupported HTTP method'}, status=405)

def home_videos(request):
	if request.method == 'GET':
		response = []
		for x in UserVideo.objects.all(): # Aquí se podría tunear para que en la /home se devolviera algo distinto a TODOS LOS VIDEOS
			response.append(x.to_obj())
		return JsonResponse(response, safe=False, status=200)
	else:
		return JsonResponse({'error':'Unsupported HTTP method'}, status=405)
		
@csrf_exempt
def user_videos(request):
	user = __get_logged_user(request)
	if user is None:
		return JsonResponse({'error': 'Not logged in. Missing Session-Token header?'}, status=401)
	if request.method == 'GET':
		response = []
		for x in UserVideo.objects.filter(user=user):
			response.append(x.to_obj())
		return JsonResponse(response, safe=False, status=200)
	elif request.method == 'POST':
		body = json.loads(request.body)
		new_video_url = body.get('video_url', None)
		new_image_url = body.get('image_url', None)
		new_agent_name = body.get('agent_name', None)
		if new_video_url is None or new_image_url is None or new_agent_name is None:
			return JsonResponse({'error': 'Missing field video_url, image_url or agent_name in request body'}, status=400)
		new_video = UserVideo(video_url=new_video_url, image_url=new_image_url, agent_name=new_agent_name, user=user)
		new_video.save()
		return JsonResponse({'updated': 'ok'}, status=200)
	else:
		return JsonResponse({'error':'Unsupported HTTP method'}, status=405)

def __get_logged_user(request):
	session_token = request.headers.get(SESSION_TOKEN_HEADER, None)
	if session_token == None:
		return None
	try:
		session = UserSession.objects.get(token=session_token)
		return session.user
	except UserSession.DoesNotExist:
		return None
		
