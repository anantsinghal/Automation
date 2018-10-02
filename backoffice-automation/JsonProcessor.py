import json
import os

from json2html import json2html

ENVIRONMENT_VARIABLE_NAME = 'BO_Automation_ITF_Json'
FULL_ENVIRONMENT_PATH = os.environ.get(ENVIRONMENT_VARIABLE_NAME)


def get_list_of_all_file_names_in_directory(directory):
    file_list = [f for f in os.listdir(FULL_ENVIRONMENT_PATH) if f.endswith(".json")]
    return file_list


def get_html_of_all_files(file_list):
    htm_string_complete = ''
    if file_list:
        for file_name in file_list:
            full_file_path = os.path.join(FULL_ENVIRONMENT_PATH, file_name)
            json_file = get_json_from_location(full_file_path)
            html_str = json2html.convert(json=json_file)
            htm_string_complete += html_str

        # file_list = [f for f in os.listdir(FULL_ENVIRONMENT_PATH) if f.endswith(".json")]
        for f in file_list:
            os.remove(os.path.join(FULL_ENVIRONMENT_PATH, f))

    return htm_string_complete;


def get_json_from_location(file_location):
    with open(file_location) as json_data:
        str_json = json.load(json_data)
        return str_json


print(get_html_of_all_files(get_list_of_all_file_names_in_directory(FULL_ENVIRONMENT_PATH)))
