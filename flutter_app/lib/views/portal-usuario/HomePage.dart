import 'package:flutter/material.dart';
import 'package:flutter_app/components/DrawerNavigation.dart';

class HomePage extends StatelessWidget {
  const HomePage({super.key, required this.email});

  final String email;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Home Page'),
      ),
      drawer: DrawerNavigation(email: email),
      body: Center(
        child: Padding(
          padding: const EdgeInsets.symmetric(horizontal: 18, vertical: 16),
          child: SingleChildScrollView(
            child: Column(
              children: [
                Card(
                  color: Colors.grey[300],
                  elevation: 8.0,
                  child: Container(
                    padding: const EdgeInsets.symmetric(vertical: 16, horizontal: 16),
                    height: 200,
                    width: MediaQuery.of(context).size.width, //width 100%
                    child: const Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            CircleAvatar(
                              radius: 50, //we give the image a radius of 50
                              backgroundImage: NetworkImage('https://webstockreview.net/images/male-clipart-professional-man-3.jpg'),
                            ),
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.end,
                              children: [
                                // Container(
                                //   margin: EdgeInsets.only(top: 8),
                                //   width: 150,
                                //   color: Colors.black54,
                                //   height: 2,
                                // ),
                                SizedBox(height: 4),
                                Text('+2348012345678'),
                                Text('Chelsea City'),
                                Text('Flutteria'),
                              ],
                            ),
                          ],
                        ),
                        SizedBox(height: 20),
                          
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text(
                                  'John Doe',
                                  style: TextStyle(
                                    fontSize: 16,
                                    fontWeight: FontWeight.bold,
                                  ),
                                ),
                                SizedBox(height: 4),
                                Text('JohnDee'),
                              ],
                            ),
                            SizedBox(width: 32),
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.end,
                              children: [
                                Text(
                                  'Mobile App Developer',
                                  style: TextStyle(
                                    fontSize: 16,
                                    fontWeight: FontWeight.bold,
                                  ),
                                ),
                                SizedBox(height: 4),
                                Text('FlutterStars Inc'),
                              ],
                            )
                          ],
                        ),
                      ],
                    )
                  ),
                ),
                Card(
                  color: Colors.grey[300],
                  elevation: 8.0,
                  child: Container(
                    padding: const EdgeInsets.symmetric(vertical: 16, horizontal: 16),
                    height: 200,
                    width: MediaQuery.of(context).size.width, //width 100%
                    child: const Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            CircleAvatar(
                              radius: 50, //we give the image a radius of 50
                              backgroundImage: NetworkImage('https://webstockreview.net/images/male-clipart-professional-man-3.jpg'),
                            ),
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.end,
                              children: [
                                // Container(
                                //   margin: EdgeInsets.only(top: 8),
                                //   width: 150,
                                //   color: Colors.black54,
                                //   height: 2,
                                // ),
                                SizedBox(height: 4),
                                Text('+2348012345678'),
                                Text('Chelsea City'),
                                Text('Flutteria'),
                              ],
                            ),
                          ],
                        ),
                        SizedBox(height: 20),
                          
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text(
                                  'John Doe',
                                  style: TextStyle(
                                    fontSize: 16,
                                    fontWeight: FontWeight.bold,
                                  ),
                                ),
                                SizedBox(height: 4),
                                Text('JohnDee'),
                              ],
                            ),
                            SizedBox(width: 32),
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.end,
                              children: [
                                Text(
                                  'Mobile App Developer',
                                  style: TextStyle(
                                    fontSize: 16,
                                    fontWeight: FontWeight.bold,
                                  ),
                                ),
                                SizedBox(height: 4),
                                Text('FlutterStars Inc'),
                              ],
                            )
                          ],
                        ),
                      ],
                    )
                  ),
                ),
                Card(
                  color: Colors.grey[300],
                  elevation: 8.0,
                  child: Container(
                    padding: const EdgeInsets.symmetric(vertical: 16, horizontal: 16),
                    height: 200,
                    width: MediaQuery.of(context).size.width, //width 100%
                    child: const Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            CircleAvatar(
                              radius: 50, //we give the image a radius of 50
                              backgroundImage: NetworkImage('https://webstockreview.net/images/male-clipart-professional-man-3.jpg'),
                            ),
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.end,
                              children: [
                                // Container(
                                //   margin: EdgeInsets.only(top: 8),
                                //   width: 150,
                                //   color: Colors.black54,
                                //   height: 2,
                                // ),
                                SizedBox(height: 4),
                                Text('+2348012345678'),
                                Text('Chelsea City'),
                                Text('Flutteria'),
                              ],
                            ),
                          ],
                        ),
                        SizedBox(height: 20),
                          
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text(
                                  'John Doe',
                                  style: TextStyle(
                                    fontSize: 16,
                                    fontWeight: FontWeight.bold,
                                  ),
                                ),
                                SizedBox(height: 4),
                                Text('JohnDee'),
                              ],
                            ),
                            SizedBox(width: 32),
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.end,
                              children: [
                                Text(
                                  'Mobile App Developer',
                                  style: TextStyle(
                                    fontSize: 16,
                                    fontWeight: FontWeight.bold,
                                  ),
                                ),
                                SizedBox(height: 4),
                                Text('FlutterStars Inc'),
                              ],
                            )
                          ],
                        ),
                      ],
                    )
                  ),
                ),
                Card(
                  color: Colors.grey[300],
                  elevation: 8.0,
                  child: Container(
                    padding: const EdgeInsets.symmetric(vertical: 16, horizontal: 16),
                    height: 200,
                    width: MediaQuery.of(context).size.width, //width 100%
                    child: const Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            CircleAvatar(
                              radius: 50, //we give the image a radius of 50
                              backgroundImage: NetworkImage('https://webstockreview.net/images/male-clipart-professional-man-3.jpg'),
                            ),
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.end,
                              children: [
                                // Container(
                                //   margin: EdgeInsets.only(top: 8),
                                //   width: 150,
                                //   color: Colors.black54,
                                //   height: 2,
                                // ),
                                SizedBox(height: 4),
                                Text('+2348012345678'),
                                Text('Chelsea City'),
                                Text('Flutteria'),
                              ],
                            ),
                          ],
                        ),
                        SizedBox(height: 20),
                          
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text(
                                  'John Doe',
                                  style: TextStyle(
                                    fontSize: 16,
                                    fontWeight: FontWeight.bold,
                                  ),
                                ),
                                SizedBox(height: 4),
                                Text('JohnDee'),
                              ],
                            ),
                            SizedBox(width: 32),
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.end,
                              children: [
                                Text(
                                  'Mobile App Developer',
                                  style: TextStyle(
                                    fontSize: 16,
                                    fontWeight: FontWeight.bold,
                                  ),
                                ),
                                SizedBox(height: 4),
                                Text('FlutterStars Inc'),
                              ],
                            )
                          ],
                        ),
                      ],
                    )
                  ),
                ),
                Card(
                  color: Colors.grey[300],
                  elevation: 8.0,
                  child: Container(
                    padding: const EdgeInsets.symmetric(vertical: 16, horizontal: 16),
                    height: 200,
                    width: MediaQuery.of(context).size.width, //width 100%
                    child: const Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            CircleAvatar(
                              radius: 50, //we give the image a radius of 50
                              backgroundImage: NetworkImage('https://webstockreview.net/images/male-clipart-professional-man-3.jpg'),
                            ),
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.end,
                              children: [
                                // Container(
                                //   margin: EdgeInsets.only(top: 8),
                                //   width: 150,
                                //   color: Colors.black54,
                                //   height: 2,
                                // ),
                                SizedBox(height: 4),
                                Text('+2348012345678'),
                                Text('Chelsea City'),
                                Text('Flutteria'),
                              ],
                            ),
                          ],
                        ),
                        SizedBox(height: 20),
                          
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text(
                                  'John Doe',
                                  style: TextStyle(
                                    fontSize: 16,
                                    fontWeight: FontWeight.bold,
                                  ),
                                ),
                                SizedBox(height: 4),
                                Text('JohnDee'),
                              ],
                            ),
                            SizedBox(width: 32),
                            Column(
                              crossAxisAlignment: CrossAxisAlignment.end,
                              children: [
                                Text(
                                  'Mobile App Developer',
                                  style: TextStyle(
                                    fontSize: 16,
                                    fontWeight: FontWeight.bold,
                                  ),
                                ),
                                SizedBox(height: 4),
                                Text('FlutterStars Inc'),
                              ],
                            )
                          ],
                        ),
                      ],
                    )
                  ),
                ),
              ],
            ),
          )
        )
      ),
    );
  }
}