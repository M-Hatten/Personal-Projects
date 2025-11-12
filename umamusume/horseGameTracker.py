# Hello to anyone reading this!
# This application is meant to help me track my characters in a mobile game I like to play

# Capabilities
# - Lists each umamusume
# - Tracks race stats (Track type, Distance, and Style)
# - Tracks highest graded veteran score
# - Tracks what track they use in Team Trials
# - Tracks umamusume tier
# 
# Data will be kept in a dictionary for ease of access and will be stored to a json file
# This is mostly all just for practice to keep my skills sharp


import json 

#Variables
umamusume = {}

print("Hello! Today's a good day to play Umamusume! :D\n")


with open('umamusumeData.json') as json_file:
    umamusume = json.load(json_file)


