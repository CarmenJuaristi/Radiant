from django.db import models

class CustomUser(models.Model):
	username = models.CharField(unique=True, max_length=50)
	encrypted_password = models.CharField(max_length=100)
	email = models.CharField(unique=True, max_length=200)

	def __str__(self):
		return self.username

class UserSession(models.Model):
	user = models.ForeignKey(CustomUser, on_delete=models.CASCADE)
	token = models.CharField(unique=True, max_length=20)

	def __str__(self):
		return str(self.user) + ' - ' + self.token
		
class UserVideo(models.Model):
	image_url = models.CharField(max_length=500)
	video_url = models.CharField(max_length=500)
	agent_name = models.CharField(max_length = 50)
	user = models.ForeignKey(CustomUser, on_delete=models.CASCADE)
	
	def to_obj(self):
		return {
		"image": self.image_url,
		"video": self.video_url,
		"uploader": self.user.username,
		"agent": self.agent_name
		}
